/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,js}", './node_modules/flowbite-react/lib/esm/**/*.js'],
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
    require('flowbite/plugin'),
    require('@tailwindcss/forms'),
  ],

}

