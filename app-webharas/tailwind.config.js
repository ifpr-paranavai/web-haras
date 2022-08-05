/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./pages/**/*.{js,ts,jsx,tsx}",
    "./components/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          light: "#2e69a3", // For lighter primary color
          DEFAULT: "#1b4c76", // Normal primary color
          dark: "#12344c", // Used for hover, active, etc.
        },
      },
    },
  },
  plugins: [
    require("kutty")
  ],
}
