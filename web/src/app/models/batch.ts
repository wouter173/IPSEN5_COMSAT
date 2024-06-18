import { z } from 'zod';
import { contactSchema } from './contact';
import { statusSchema } from './status';
import { templateSchema } from './templates';

export const batchContactSchema = contactSchema.merge(
  z.object({
    hidden: z.boolean(),
    status: statusSchema,
  }),
);

export const batchSchema = z.object({
  id: z.string(),
  state: statusSchema,
  name: z.string(),
  contacts: z.array(batchContactSchema),
  createdAt: z.coerce.date(),
  lastModified: z.coerce.date(),
  templates: z.array(templateSchema),
});

export type Batch = z.infer<typeof batchSchema>;
