import { useEffect, useState } from 'react'
import sensesService from '../services/senses'
import type { SensoryContent, UserSensoryPreference } from '../types'

export default function SensoryContentPage() {
  const [items, setItems] = useState<SensoryContent[]>([])
  const [favorites, setFavorites] = useState<UserSensoryPreference[]>([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)
  // inline expansion state for each card
  const [expandedIds, setExpandedIds] = useState<number[]>([])
  const [pendingFavs, setPendingFavs] = useState<number[]>([])

  // Shared favorite toggler used by cards and expanded panels to keep state in sync
  const toggleFavoriteById = async (id?: number) => {
    if (id == null) return
    if (pendingFavs.includes(id)) return
    setPendingFavs((p) => [...p, id])
    try {
      const res = await sensesService.toggleFavorite(String(id))
      const nowFav = !!res?.favorite || res === true
      setFavorites((prev) => {
        if (nowFav) {
          const entry: any = res?.id ? res : { content: { id }, favorite: true, createdAt: new Date().toISOString() }
          return [...prev.filter((f: any) => ((f as any).content?.id ?? (f as any).contentId ?? null) !== id), entry]
        }
        return prev.filter((f: any) => ((f as any).content?.id ?? (f as any).contentId ?? null) !== id)
      })
    } catch (err) {
      // eslint-disable-next-line no-console
      console.warn('toggleFavorite failed', err)
    } finally {
      setPendingFavs((p) => p.filter((x) => x !== id))
    }
  }

  useEffect(() => {
    setLoading(true)
    Promise.all([sensesService.listSenses(), sensesService.getMyFavorites()])
      .then(([list, favs]) => {
        // Defensive: only set arrays. If the response is wrapped or a string
        // (e.g. index.html served), coerce to empty array and warn.
        if (Array.isArray(list)) setItems(list)
        else if (Array.isArray((list as any)?.data)) setItems((list as any).data)
        else {
          // eslint-disable-next-line no-console
          console.warn('SensoryContent: unexpected list shape', list)
          setItems([])
        }

        if (Array.isArray(favs)) setFavorites(favs)
        else if (Array.isArray((favs as any)?.data)) setFavorites((favs as any).data)
        else {
          // eslint-disable-next-line no-console
          console.warn('SensoryContent: unexpected favorites shape', favs)
          setFavorites([])
        }
      })
      .catch((err) => setError(String(err?.body?.message || err?.message || err)))
      .finally(() => setLoading(false))
  }, [])

  if (loading) return <div>Cargando contenido sensorial...</div>
  if (error) return <div>Error: {error}</div>

  return (
  <div className="sensory-content">
      <h2 className="text-xl font-bold">Contenido Sensorial</h2>
      <div className="text-sm text-gray-600 mb-2">Favoritos: {favorites.length}</div>
  <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        {items.map((c) => {
          const openDetail = async () => {
            // Toggle inline expansion under the card
            const id = c.id
            if (id == null) return
            const willExpand = !expandedIds.includes(id)
            // optimistic toggle UI
            setExpandedIds((prev) => (prev.includes(id) ? prev.filter((x) => x !== id) : [...prev, id]))
            if (willExpand) {
              try {
                await sensesService.increaseView(String(id))
                setItems((prev) => prev.map((it) => (it.id === id ? { ...it, viewCount: (it.viewCount ?? 0) + 1 } : it)))
              } catch (e) {
                // eslint-disable-next-line no-console
                console.warn('increaseView failed', e)
              }
            }
          }

          return (
            <div key={c.id} className="rounded-xl overflow-hidden sensory-card">
              <div
               key={c.id}
              role="button"
              tabIndex={0}
              onClick={openDetail}
              onKeyDown={(e) => { if (e.key === 'Enter' || e.key === ' ') { e.preventDefault(); openDetail() } }}
              className="relative !bg-white border border-gray-200 dark:border-gray-700 p-6 rounded-lg text-left shadow-md hover:shadow-lg transition-shadow text-gray-900 overflow-hidden ring-1 ring-black/5 dark:ring-white/5 cursor-pointer focus:outline-none"
            >
              

              <img src={c.thumbnailUrl} alt={c.title} className="w-16 h-16 object-cover mb-3 bg-white rounded-full mx-auto block" />
              <h3 className="font-semibold">{c.title}</h3>
              <p className="text-sm text-gray-600">{c.description}</p>
              <div className="flex items-center justify-between mt-2">
                <div className="text-xs text-gray-500">Vistas: {c.viewCount}</div>
                <div className="text-xs text-gray-500">{(c as any).senseType ?? (c as any).type}</div>
              </div>
            </div>

              {/* Expanded inline panel */}
              {expandedIds.includes(c.id as number) && (
                <div className="mt-2 mb-6 bg-gray-50 dark:bg-gray-800 border border-gray-100 dark:border-gray-700 rounded-xl p-6 text-sm text-gray-700 dark:text-gray-200 sensory-expanded">
                  <div className="flex items-start justify-between gap-3">
                    <div className="flex-1">
                      <div className="font-semibold mb-1">{c.title}</div>
                      {/* Prefer full/long description fields if backend provides them */}
                      {((c as any).fullDescription || (c as any).longDescription || c.description) && (
                        <p className="mb-2 text-sm text-gray-600 dark:text-gray-300">{(c as any).fullDescription ?? (c as any).longDescription ?? c.description}</p>
                      )}
                      {/* Additional metadata when available */}
                      <div className="flex flex-col gap-1 text-xs text-gray-500 dark:text-gray-400 mt-2">
                        {(c as any).durationSeconds != null && (
                          <div>Duración: {Math.floor((c as any).durationSeconds / 60)}:{String((c as any).durationSeconds % 60).padStart(2, '0')} min</div>
                        )}
                        {c.tags && <div>Tags: {c.tags}</div>}
                        {(c as any).stressLevelMin != null || (c as any).stressLevelMax != null ? (
                          <div>Stress objetivo: {(c as any).stressLevelMin ?? '-'} - {(c as any).stressLevelMax ?? '-'}</div>
                        ) : null}
                        {c.createdAt && <div>Creado: {new Date(c.createdAt).toLocaleDateString()}</div>}
                      </div>
                      {(c as any).contentUrl && (
                        <div onClick={(e) => e.stopPropagation()}>
                          {(c as any).senseType === 'AUDIO' ? (
                            <audio
                              controls
                              src={(c as any).contentUrl}
                              className="w-full mb-2"
                              onPlay={async () => {
                                try {
                                  await sensesService.registerReproduction(String(c.id))
                                } catch (e) {
                                  // ignore
                                }
                              }}
                            />
                          ) : (
                            <a href={(c as any).contentUrl} target="_blank" rel="noreferrer" className="text-blue-600 underline" onClick={async (e) => { e.stopPropagation(); try { await sensesService.registerReproduction(String(c.id)) } catch {} }}>
                              Abrir contenido
                            </a>
                          )}
                        </div>
                      )}
                      <div className="text-xs text-gray-500">Vistas: {c.viewCount}</div>
                    </div>

                    <div className="flex-shrink-0 flex flex-col items-end gap-2">
                      {/* Star-shaped favorite button: filled gold when favorite, otherwise gray border + outline */}
                      {(() => {
                        const isFav = !!favorites.find((f: any) => ((f as any).content?.id ?? (f as any).contentId ?? null) === c.id)
                        return (
                          <button
                            aria-label={isFav ? 'Quitar favorito' : 'Marcar favorito'}
                            onClick={async (e) => { e.stopPropagation(); await toggleFavoriteById(c.id) }}
                            disabled={c.id == null ? true : pendingFavs.includes(c.id)}
                            className={`btn-fav ${isFav ? 'active pulse' : ''} ${pendingFavs.includes(c.id as number) ? 'opacity-60' : ''}`}
                            aria-pressed={isFav}
                          >
                            {pendingFavs.includes(c.id as number) ? (
                              <svg className="fav-spinner" viewBox="0 0 50 50" aria-hidden>
                                <circle cx="25" cy="25" r="20" fill="none" strokeWidth="4" stroke="currentColor" strokeLinecap="round" strokeDasharray="31.4 31.4" />
                              </svg>
                            ) : isFav ? (
                              <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden>
                                <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.286 3.966a1 1 0 00.95.69h4.175c.969 0 1.371 1.24.588 1.81l-3.38 2.455a1 1 0 00-.364 1.118l1.287 3.966c.3.921-.755 1.688-1.54 1.118L10 13.348l-3.52 2.702c-.784.57-1.838-.197-1.539-1.118l1.287-3.966a1 1 0 00-.364-1.118L2.484 9.393c-.783-.57-.38-1.81.588-1.81h4.175a1 1 0 00.95-.69L9.05 2.927z" />
                              </svg>
                            ) : (
                              <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5" aria-hidden>
                                <path strokeLinecap="round" strokeLinejoin="round" d="M11.48 3.499a1 1 0 011.04 0l1.286 3.966a1 1 0 00.95.69h4.175c.969 0 1.371 1.24.588 1.81l-3.38 2.455a1 1 0 00-.364 1.118l1.287 3.966c.3.921-.755 1.688-1.54 1.118L12 14.92l-3.52 2.702c-.784.57-1.838-.197-1.539-1.118l1.287-3.966a1 1 0 00-.364-1.118L3.484 9.372c-.783-.57-.38-1.81.588-1.81h4.175a1 1 0 00.95-.69L11.48 3.5z" />
                              </svg>
                            )}
                          </button>
                        )
                      })()}
                    </div>
                  </div>
                </div>
              )}
            </div>
          )
        })}
      </div>

      
    </div>
  )
}
