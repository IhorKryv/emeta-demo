export class Board {
  id: string | undefined;
  adminId: string | undefined;
  workplaceId: string | undefined;
  name: string | undefined;
  customName: string | undefined;
  description: string | undefined;
  customDescription: string | undefined;
  image: string | undefined;
  imageUrl: string | undefined;
  boardInCollection: boolean | undefined;

  //Wrapper, fix later
  file: File | undefined;
}
