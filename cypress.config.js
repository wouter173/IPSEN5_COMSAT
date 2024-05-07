const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
    baseURL: "http://localhost:4200",
    },
});
