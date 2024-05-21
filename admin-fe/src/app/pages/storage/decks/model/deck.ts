import {Category} from "../../categories/model/category";

export class Deck {
  id: string | undefined;
  name: string | undefined;
  description: string | undefined;
  cardBack: string | undefined;
  cardBackUrl: string | undefined;
  cardsCount: number | undefined;
  available: boolean | undefined;
  premium: boolean | undefined;
  categories: {id: string | undefined }[] | Category[] | undefined
}
