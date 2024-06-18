describe("Batch", () => {

  it("Should send a Batch", () => {
    cy.visit('/login');

    cy.get('input[name="username"]').type('admin@gmail.com');
    cy.get('input[name="password"]').type('admin');
    cy.get('button[type="submit"]').click();
    cy.wait(500);
    cy.visit("/batches");


    cy.get('[data-cy="batch-select"]').click();
    cy.get('[data-cy="send-batch"]').click()
    cy.wait(4000)

    cy.get('[data-cy="sent"]').contains('Sent!').should('be.visible');
  });

  it("Should delete a contact", () => {
    cy.visit('/login');
    cy.get('input[name="username"]').type('admin@gmail.com');
    cy.get('input[name="password"]').type('admin');
    cy.get('button[type="submit"]').click();
    cy.wait(500);
    cy.visit("/batches");
    cy.get('[data-cy="batch-select"]').click();

    cy.get('[data-cy="delete-contact"]').first().click()
  });

  it("Should edit a contact", () => {
    cy.visit('/login');
    cy.get('input[name="username"]').type('admin@gmail.com');
    cy.get('input[name="password"]').type('admin');
    cy.get('button[type="submit"]').click();
    cy.wait(500);
    cy.visit("/batches");

    cy.get('[data-cy="batch-select"]').click();

    cy.get('[data-cy="edit-contact"]').first().click()
    cy.get('[data-cy="input-fullname"]').clear().type('John')

    cy.get('[data-cy="button-save-contact"]').first().click()

  });


});
