export class ShortInfo {
  profileId: string | undefined;
  title: string | undefined;
  description: string | undefined;
  cards: ShortInfoCard[] = [];
}

export class ShortInfoCard {
  id: string | undefined;
  icon: string | undefined;
  title: string | undefined;
  text: string | undefined;
}
