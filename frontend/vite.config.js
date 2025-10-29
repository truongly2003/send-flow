import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

import path from "path";
import { fileURLToPath } from "url";
import { dirname } from "path";
const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
   resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
      "@components": path.resolve(__dirname, "./src/components"),
      "@layouts": path.resolve(__dirname, "./src/layouts"),
      "@pages": path.resolve(__dirname, "./src/pages"),
      "@services": path.resolve(__dirname, "./src/services"),
      "@configs": path.resolve(__dirname, "./src/configs"),
      "@routes": path.resolve(__dirname, "./src/routes"),
      "@admins": path.resolve(__dirname, "./src/pages/admins"),
      "@clients": path.resolve(__dirname, "./src/pages/clients"),
      "@users": path.resolve(__dirname, "./src/pages/users"),
      "@assets": path.resolve(__dirname, "./src/assets"),
      "@utils": path.resolve(__dirname, "./src/utils"),
      "@redux": path.resolve(__dirname, "./src/redux"),
      "@features": path.resolve(__dirname, "./src/features"),
    },
  },
})

