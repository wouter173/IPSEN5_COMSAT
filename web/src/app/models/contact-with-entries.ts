import { z } from 'zod';
import { platforms } from './platform';
import { statusSchema } from './status';

export const contactWithEntriesSchema = z.object({
  id: z.string(),
  platform: z.enum(platforms),
  firstName: z.string().optional(),
  nickname: z.string().optional(),
  audience: z.string().optional(),
  sex: z.string().optional(),
  language: z.string().optional(),
  region: z.string().optional(),
  entries: z.array(
    z.object({
      id: z.string(),
      status: statusSchema,
      message: z.string().nullable(),
      hidden: z.boolean(),
      batchId: z.string().nullable(),
      batchName: z.string().nullable(),
      createdAt: z.coerce.date(),
      lastModified: z.coerce.date(),
    }),
  ),
});

export type ContactWithEntries = z.infer<typeof contactWithEntriesSchema>;
