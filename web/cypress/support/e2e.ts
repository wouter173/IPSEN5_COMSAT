// ***********************************************************
// This example support/e2e.ts is processed and
// loaded automatically before your test files.
//
// This is a great place to put global configuration and
// behavior that modifies Cypress.
//
// You can change the location of this file or turn off
// automatically serving support files with the
// 'supportFile' configuration option.
//
// You can read more here:
// https://on.cypress.io/configuration
// ***********************************************************

// Import commands.js using ES2015 syntax:
import './commands';

// Alternatively you can use CommonJS syntax:
// require('./commands')

declare global {
  namespace Cypress {
    interface Chainable<Subject> {
      login(): Chainable<Subject>;
    }
  }
}

Cypress.Commands.add('login', () => {
  cy.request({
    method: 'POST',
    url: 'http://localhost:8080/api/v1/auth/login',
    body: {
      username: 'admin@gmail.com',
      password: 'admin',
    },
  }).then((resp) => {
    window.localStorage.setItem('bearer ', resp.body.token);
  });
});
