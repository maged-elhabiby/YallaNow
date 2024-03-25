/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
    "./public/index.html",
  ],
  theme: {
    extend: {    
      colors: {
        'slate-3': '#252a34',
        'pink-2': '#f7559a',
        'slate-1': '#3a4252',
        'slate-3': '#101216',
        'pink-1': '#f986b7',
      }
    },

  },
  plugins: [
    require('@tailwindcss/forms'),
    require('@tailwindcss/typography'),
    require('@tailwindcss/aspect-ratio'),
  ],
}

