import { z } from 'zod';
import { contactSchema } from './contact';

export const batchContactSchema = contactSchema.merge(
  z.object({
    hidden: z.boolean(),
    status: z.enum(['NOTSENT', 'SENDING', 'SENT', 'ERROR', 'READ', 'REPLIED']),
  }),
);

export const batchSchema = z.object({
  id: z.string(),
  state: z.enum(['NOTSENT', 'SENDING', 'SENT']),
  name: z.string(),
  contacts: z.array(batchContactSchema),
  createdAt: z.coerce.date(),
  lastModified: z.coerce.date(),
});

export type Batch = z.infer<typeof batchSchema>;
