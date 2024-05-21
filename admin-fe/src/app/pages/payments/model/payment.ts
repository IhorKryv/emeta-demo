export class Payment {
  id: string | undefined;
  description: string | undefined;
  type: string | undefined;
  productId: string | undefined;
  status: string | undefined;
  workplace: { id: string, firstName?: string, lastName?: string } | undefined;
  createdAt: Date | undefined;
  updatedAt: Date | undefined;
}
