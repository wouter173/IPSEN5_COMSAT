describe('Template', () => {
  it('Create template', () => {
    cy.login('admin@gmail.com', 'admin');
    cy.wait(1000);

    cy.contains('Templates').click();

    cy.wait(1000);

    let title;

    cy.get('app-template-list-item').its('length').then((len) => {
      const randomIndex = Math.floor(Math.random() * len);
      cy.get('app-template-list-item').eq(randomIndex).click();

      cy.get('input[id="header"]').invoke('val').then((val) => {
        title = val;
      })
    });

    cy.wait(1000);

    cy.get('button').find('lucide-icon[name="plus-icon"]').click();

    cy.get('input[id="header"]').type('Random Title');

    cy.get('select[id="platform"]').select('snapchat');

    cy.get('ngx-editor[id="editor"]').type('Random Template Text');

cy.scrollTo('topRight');
cy.wait(500);
cy.scrollTo('topRight');
cy.wait(500);
cy.get('button').contains('Save').scrollTo('topRight').click();
  });
});
