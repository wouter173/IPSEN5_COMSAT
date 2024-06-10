import { z } from 'zod';
import { batchContactSchema } from './batch';
import { platforms } from './platform';

export const templateSchema = z.object({
  id: z.string(),
  platform: z.enum(platforms),
  header: z.string(),
  body: z.string(),
  metadata: z.string(),
  lastModified: z.string(),
  createdAt: z.string(),
  translations: z
    .array(
      z.object({
        language: z.string(),
        body: z.string(),
      }),
    )
    .optional(),
});

export type Template = z.infer<typeof templateSchema>;
