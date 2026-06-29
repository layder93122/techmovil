import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

const TOKEN = "Bearer sk_16558.36uckoUCQU8XLg2FTN4UnXTogrfsc4TI";

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    host: true,
    proxy: {
      "/api/dni": {
        target: "https://api.apis.net.pe/v1",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/dni/, "/dni"),
        headers: { Authorization: TOKEN },
      },
      "/api/ruc": {
        target: "https://api.apis.net.pe/v1",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/ruc/, "/ruc"),
        headers: { Authorization: TOKEN },
      },
    },
  },
});
