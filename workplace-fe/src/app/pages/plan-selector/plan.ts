export class Plan {
  id: string | undefined;
  name: string | undefined;
  description: string | undefined;
  price: string | undefined;
  duration: PlanDuration | undefined;
  enabled: boolean | undefined;
  defaultPlan: boolean | undefined;
  macSettings: MacSettings = new MacSettings();
  preferences: PlanPreference[] = [];
}

export enum PlanDuration {
  DAY = "Day", WEEK = "Week", MONTH = "Month", THREE_MONTHS = "Three months", SIX_MONTHS = "Six months", YEAR = "Year"
}

export class MacSettings {
  id: string | undefined;
  numberOfFreeDecks: number | undefined;
  numberOfFreeBoards: number | undefined;
  maxGroupSize: number | undefined;
  personalPage: boolean | undefined;
  allowOwnCards: boolean | undefined;
}

export class PlanPreference {
  id: string | undefined;
  key: string | undefined;
  name: string | undefined;
  description: string | undefined;
}

export class MyPlan {
  plan: Plan = new Plan();
  expiringAt: Date = new Date();
  expired: boolean | undefined;
  workplaceStatus: string | undefined;
}
