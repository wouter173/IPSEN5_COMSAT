describe('Batch', () => {
  it('Navigates to Batches', () => {
    cy.login('admin@gmail.com', 'admin');
    cy.wait(300);

    cy.contains('Batches').click();
    cy.get('app-batch-list-item').first().click();
    cy.wait(1000); // Add a delay to allow the page to fully load
  });

it('Edits a contact', () => {
  cy.login('admin@gmail.com', 'admin');
  cy.wait(300);

  cy.contains('Batches').click();
  cy.get('app-batch-list-item').first().click();

  // Get all the contacts
  cy.get('YOUR_CORRECT_CONTACT_SELECTOR').then(($contacts) => {
    // Generate a random index
    const randomIndex = Math.floor(Math.random() * $contacts.length);

    // Click the edit button of the contact at the random index
    cy.get('YOUR_CORRECT_CONTACT_SELECTOR').eq(randomIndex).find('YOUR_EDIT_BUTTON_SELECTOR').click();

    // Change the value of the Fullname input field
    cy.get('YOUR_FULLNAME_INPUT_SELECTOR').clear().type('New Fullname');

    // Click the save button
    cy.get('YOUR_SAVE_BUTTON_SELECTOR').click();
  });
});
});
