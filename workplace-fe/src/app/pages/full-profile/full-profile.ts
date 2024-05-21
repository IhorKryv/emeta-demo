import {Banner} from "../profile/builder/banner/banner";
import {ShortInfo} from "../profile/builder/short-info/short-info";
import {Schedule} from "../profile/builder/schedule/schedule";
import {ProfileDeck} from "../profile/builder/my-decks/profile-deck";
import {ProfileContact} from "../profile/builder/contacts/profile-contact";

export class FullProfile {
  profileId: string | undefined;
  profileURL: string | undefined;
  banner: Banner = new Banner();
  shortInfo: ShortInfo = new ShortInfo();
  schedule: Schedule = new Schedule();
  profileDeck: ProfileDeck = new ProfileDeck();
  profileContact: ProfileContact = new ProfileContact();
}
