export class ProfileDeck {
  id: string | undefined;
  profileId: string | undefined;
  title: string | undefined;
  info: string | undefined;
  decks: ProfileDeckItem[] = [];
}

export class ProfileDeckItem {
  id: string | undefined;
  image: string | undefined;
  title: string | undefined;
  info: string | undefined;
}
