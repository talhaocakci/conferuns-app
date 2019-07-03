export const enum ConferenceTopic {
  SOFTWARE_ENGINEERING = 'SOFTWARE_ENGINEERING',
  FINANCE = 'FINANCE',
  SPORTS = 'SPORTS'
}

export const enum Audience {
  BEGINNER = 'BEGINNER',
  MID_LEVEL = 'MID_LEVEL',
  EXPERIENCED = 'EXPERIENCED'
}

export interface ISubject {
  id?: number;
  topic?: ConferenceTopic;
  difficulty?: number;
  audience?: Audience;
}

export const defaultValue: Readonly<ISubject> = {};
