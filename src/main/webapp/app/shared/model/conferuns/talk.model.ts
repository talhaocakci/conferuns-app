import { IFile } from 'app/shared/model/conferuns/file.model';
import { ITalkParticipant } from 'app/shared/model/conferuns/talk-participant.model';
import { IConference } from 'app/shared/model/conferuns/conference.model';
import { ITalkTag } from 'app/shared/model/conferuns/talk-tag.model';

export const enum Language {
  ENGLISH = 'ENGLISH',
  TURKISH = 'TURKISH',
  FRENCH = 'FRENCH',
  SPANISH = 'SPANISH'
}

export const enum TalkStatus {
  DRAFT = 'DRAFT',
  IN_REVIEW = 'IN_REVIEW',
  NEED_MORE_REVIEW = 'NEED_MORE_REVIEW',
  REJECTED = 'REJECTED',
  APPROVED = 'APPROVED'
}

export interface ITalk {
  id?: number;
  language?: Language;
  mainTopic?: string;
  subTopic?: string;
  status?: TalkStatus;
  presenterId?: number;
  files?: IFile[];
  participants?: ITalkParticipant[];
  conferences?: IConference[];
  tags?: ITalkTag[];
}

export const defaultValue: Readonly<ITalk> = {};
