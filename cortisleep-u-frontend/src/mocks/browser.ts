import handlers from './handlers'

async function tryImport(path: string) {
	try {
		// dynamic import - bundlers may rewrite these but it's worth trying
		return await import(/* @vite-ignore */ path)
	} catch (err) {
		return null
	}
}

// Try several possible msw import paths and resolve setupWorker
async function resolveSetupWorker() {
		const candidates = [
			// Try a local shim first (stable, points to node_modules built entry)
			'./msw-shim',
			'msw',
			'msw/browser',
			'msw/lib/esm/setupWorker',
			'msw/lib/setupWorker',
			'msw/lib/browser',
		]

	for (const p of candidates) {
		const mod = await tryImport(p)
		if (!mod) continue
		if (typeof (mod as any).setupWorker === 'function') return (mod as any).setupWorker
		if (typeof (mod as any).default?.setupWorker === 'function') return (mod as any).default.setupWorker
		if (typeof mod === 'function') return mod
	}

	return null
}

export async function startWorker(options: any = {}) {
	const setupWorker = await resolveSetupWorker()
	if (!setupWorker) {
		// eslint-disable-next-line no-console
		console.error('MSW: setupWorker could not be resolved via dynamic import.');
		throw new Error('msw: setupWorker not found.');
	}

	const resolvedHandlers = Array.isArray((handlers as any)) ? (handlers as any) : [(handlers as any)]
	const worker = setupWorker(...resolvedHandlers)
	await worker.start(options)
	return worker
}
