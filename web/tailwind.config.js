/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,ts}"],
  theme: {
    extend: {
      colors: {
        base: { 1: "#373737", 2: "#646464", 3: "#979797", 4: "#CACACA", 5: "#F2F2F2", 6: "#CACACA57" },
        "primary-red": { 1: "#D76B59", 2: "#F4A090", 3: "#F2B7B0" },
        "primary-blue": { 1: "#202859", 2: "#4B5E84", 3: "#6D7FAC" },
        "secondary-light": { 1: "#F2F2F2", 2: "#F5F5F5", 3: "#FFFFFF" },
      },
      fontFamily: {
        sans: ["Raleway", "sans-serif"],
      },
    },
  },
  plugins: [],
};
