import { Schema } from 'prosemirror-model';
import { schema as baseSchema } from 'prosemirror-schema-basic';
import { placeholderNode } from './custom-schema';

const schema = new Schema({
  nodes: baseSchema.spec.nodes.append({ placeholder: placeholderNode }),
  marks: baseSchema.spec.marks,
});

export default schema;
