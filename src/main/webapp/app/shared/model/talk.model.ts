import { IFile } from 'app/shared/model/file.model';
import { ITalkParticipant } from 'app/shared/model/talk-participant.model';
import { IConference } from 'app/shared/model/conference.model';
import { ITalkTag } from 'app/shared/model/talk-tag.model';
import { Language } from 'app/shared/model/enumerations/language.model';
import { TalkStatus } from 'app/shared/model/enumerations/talk-status.model';

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
