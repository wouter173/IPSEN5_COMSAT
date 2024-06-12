import { z } from 'zod';

const statusses = z.union([
  z.literal('NOTSENT'),
  z.literal('SENDING'),
  z.literal('SENT'),
  z.literal('ERROR'),
  z.literal('READ'),
  z.literal('REPLIED'),
  z.literal('QUEUED'),
  z.literal('DELIVERED'),
  z.literal('USERNAME NOT FOUND'),
  z.literal('ANSWERED'),
]);

export const statusSchema = z.union([statusses, z.string()]);

export type Status = z.infer<typeof statusses> | (string & {});
