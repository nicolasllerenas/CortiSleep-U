type ViewKey =
  | 'home'
  | 'profile'
  | 'checkins'
  | 'sleep'
  | 'rewards'
  | 'poi'
  | 'focus'
  | 'screentime'
  | 'sensory'
  | 'quests'

interface Props {
  current: ViewKey
  onNavigate: (v: ViewKey) => void
}

export default function MainMenu({ current, onNavigate }: Props) {
  const items: { key: ViewKey; label: string }[] = [
    { key: 'sleep', label: 'Sueño' },
    { key: 'profile', label: 'Perfil' },
    { key: 'checkins', label: 'Check-ins' },
    { key: 'rewards', label: 'Recompensas' },
    { key: 'poi', label: 'POI' },
    { key: 'focus', label: 'Focus' },
    { key: 'screentime', label: 'Screen Time' },
  { key: 'sensory', label: 'Contenido Sensorial' },
    { key: 'quests', label: 'Misiones' },
  ]

  return (
    <nav role="navigation" aria-label="Main menu" className="w-full mt-4">
      <div className="grid grid-cols-2 sm:grid-cols-4 gap-2">
        {items.map((it) => (
          <button
            key={it.key}
            onClick={() => onNavigate(it.key)}
            aria-label={`Navigate to ${it.label}`}
            aria-current={current === it.key ? 'page' : undefined}
            className={`px-3 py-3 rounded-lg border border-gray-200 text-sm text-left flex items-center gap-3 transition-shadow ${current === it.key ? 'bg-[#56B1A6] ring-1 ring-[#3FA79A] shadow-sm' : 'bg-[#A9ECE4] hover:shadow-sm'}`}
            style={{ color: current === it.key ? '#ffffff' : '#04201c' }}
          >
            <div className="w-6 h-6 flex-none text-gray-500">
              {/** simple inline icons to avoid extra deps **/}
              {it.key === 'profile' && (
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="w-5 h-5">
                  <path d="M12 12a5 5 0 100-10 5 5 0 000 10zm0 2c-4.418 0-8 2.239-8 5v1h16v-1c0-2.761-3.582-5-8-5z" />
                </svg>
              )}
              {it.key === 'checkins' && (
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="w-5 h-5">
                  <path d="M9 11l3 3L22 4l-1.5-1.5L12 11 10.5 9.5 9 11zM5 20v-2h14v2H5z" />
                </svg>
              )}
              {it.key === 'rewards' && (
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="w-5 h-5">
                  <path d="M12 2l3 6 6 .5-4.5 4 1 6L12 16l-5.5 2.5 1-6L3 8.5 9 8 12 2z" />
                </svg>
              )}
              {it.key === 'poi' && (
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="w-5 h-5">
                  <path d="M12 2C8 2 5 5 5 9c0 5 7 13 7 13s7-8 7-13c0-4-3-7-7-7zm0 9.5A2.5 2.5 0 1112 6.5a2.5 2.5 0 010 5z" />
                </svg>
              )}
              {it.key === 'focus' && (
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="w-5 h-5">
                  <path d="M12 3v2a7 7 0 100 14v2a9 9 0 110-18z" />
                </svg>
              )}
              {it.key === 'screentime' && (
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="w-5 h-5">
                  <path d="M12 1a11 11 0 100 22 11 11 0 000-22zm1 12H7v-2h6V4h2v9z" />
                </svg>
              )}
              {it.key === 'sensory' && (
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="w-5 h-5">
                  <path d="M12 2l1.5 4.5L18 8l-3 2 1 4-4-2-4 2 1-4-3-2 4.5-1.5L12 2z" />
                </svg>
              )}
              {it.key === 'quests' && (
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="w-5 h-5">
                  <path d="M4 22l1.5-6L2 11l7-1 3-6 3 6 7 1-3.5 5 1.5 6L12 18l-8 4z" />
                </svg>
              )}
            </div>
            <div className="flex-1">
              <div className="font-medium text-sm">{it.label}</div>
            </div>
          </button>
        ))}
      </div>
    </nav>
  )
}
