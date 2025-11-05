import { useState } from 'react'
import { useAuth } from '../contexts/AuthContext'

type Props = {
  onSuccess?: () => void
  onCancel?: () => void
}

export default function RegisterForm({ onSuccess, onCancel }: Props) {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [name, setName] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const { register } = useAuth()

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    setError(null)
    setLoading(true)
    try {
      await register({ email, password, name })
      setLoading(false)
      onSuccess && onSuccess()
    } catch (err: any) {
      setLoading(false)
      const msg = err?.body?.message || err?.message || 'Error en registro'
      setError(String(msg))
    }
  }

  return (
    <form onSubmit={handleSubmit} className="space-y-4 text-left">
      <div>
        <label className="block text-sm font-medium text-gray-700">Nombre</label>
        <input
          value={name}
          onChange={(e) => setName(e.target.value)}
          type="text"
          className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2"
        />
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700">Email</label>
        <input
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          type="email"
          required
          className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2"
        />
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700">Password</label>
        <input
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          type="password"
          required
          className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2"
        />
      </div>

      {error && <p className="text-sm text-red-600">{error}</p>}

      <div className="flex items-center space-x-2">
        <button type="submit" disabled={loading} className="px-4 py-2 btn-primary rounded-md">
          {loading ? 'Registrando...' : 'Registrar'}
        </button>
        <button type="button" onClick={onCancel} className="px-4 py-2 btn-cancel rounded-md">
          Cancelar
        </button>
      </div>
    </form>
  )
}
