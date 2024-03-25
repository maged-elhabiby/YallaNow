/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {},
    colors: {
      'slate-2': '#252a34',
      'pink-2': '#F7559A',
      'slate-1': '#3a4252',
      'slate-3': '#101216',
      'pink-2': '#f986b7'
    }
  },
  plugins: [
    require('@tailwindcss/forms')
  ],
}

