import { z } from 'zod';

export const userSchema = z.object({
  username: z.string(),
  role: z.string(),
  mfaEnabled: z.boolean(),
});

export type User = z.infer<typeof userSchema>;
