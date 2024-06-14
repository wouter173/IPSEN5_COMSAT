import { NodeSpec } from 'prosemirror-model';

// Define the placeholder node
export const placeholderNode: NodeSpec = {
  group: 'inline',
  inline: true,
  atom: true,
  selectable: false,
  draggable: true,
  attrs: { placeholderType: { default: 'username' } },
  linebreakReplacement: true,

  parseDOM: [
    {
      tag: 'span[data-placeholderType]',
      getAttrs: (dom) => ({ placeholderType: dom.getAttribute('data-placeholderType') }),
    },
  ],
  toDOM: (node) => ['span', { 'data-placeholderType': node.attrs['placeholderType'], class: 'placeholder' }, node.attrs['placeholderType']],
};
