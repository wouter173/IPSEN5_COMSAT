import { z } from 'zod';

export const engineSchema = z.object({
  platform: z.string(),
  status: z.union([z.literal('Unavailable'), z.literal('RequiresCaptcha'), z.literal('Available')]),
  captchaUrl: z.string().nullable(),
});

export type Engine = z.infer<typeof engineSchema>;
