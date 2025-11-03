import { useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";

function App() {
  const [count, setCount] = useState(0);

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center">
      <div className="text-center space-y-8 p-8 bg-white rounded-2xl shadow-xl max-w-md">
        <div className="flex justify-center space-x-8">
          <a
            href="https://vite.dev"
            target="_blank"
            className="hover:scale-110 transition-transform"
          >
            <img src={viteLogo} className="h-16 w-16" alt="Vite logo" />
          </a>
          <a
            href="https://react.dev"
            target="_blank"
            className="hover:scale-110 transition-transform"
          >
            <img
              src={reactLogo}
              className="h-16 w-16 animate-spin-slow"
              alt="React logo"
            />
          </a>
        </div>

        <h1 className="text-4xl font-bold text-gray-800 bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">
          CortiSleep-U
        </h1>

        <div className="space-y-4">
          <button
            onClick={() => setCount((count) => count + 1)}
            className="px-6 py-3 bg-gradient-to-r from-blue-500 to-purple-600 text-white font-semibold rounded-lg hover:from-blue-600 hover:to-purple-700 transform hover:scale-105 transition-all duration-200 shadow-lg"
          >
            Contador: {count}
          </button>

          <p className="text-gray-600">
            Edita{" "}
            <code className="bg-gray-100 px-2 py-1 rounded text-sm font-mono">
              src/App.tsx
            </code>{" "}
            y guarda para probar HMR
          </p>
        </div>

        <p className="text-sm text-gray-500">
          Haz clic en los logos de Vite y React para aprender más
        </p>
      </div>
    </div>
  );
}

export default App;
