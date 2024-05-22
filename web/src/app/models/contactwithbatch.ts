import { z } from 'zod';
import { batchContactSchema } from './batch';
import { platforms } from './platform';

export const contactWithBatchSchema = z.object({
  id: z.string(),
  platform: z.enum(platforms),
  firstName: z.string().optional(),
  nickname: z.string().optional(),
  audience: z.string().optional(),
  sex: z.string().optional(),
  language: z.string().optional(),
  region: z.string().optional(),
  batch: z.object({
    id: z.string(),
    state: z.enum(['NOTSENT', 'SENDING', 'SENT']),
    name: z.string(),    
  }),
});

export type ContactWithBatch = z.infer<typeof contactWithBatchSchema>;
