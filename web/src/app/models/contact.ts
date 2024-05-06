import { z } from 'zod';
import { platforms } from './platform';

export const contactSchema = z.object({
  id: z.string(),
  platform: z.enum(platforms),
  firstName: z.string().optional(),
  nickname: z.string().optional(),
  audience: z.string().optional(),
  sex: z.string().optional(),
  language: z.string().optional(),
  region: z.string().optional(),
});

export type Contact = z.infer<typeof contactSchema>;
