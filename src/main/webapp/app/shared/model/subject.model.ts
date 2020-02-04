import { ConferenceTopic } from 'app/shared/model/enumerations/conference-topic.model';
import { Audience } from 'app/shared/model/enumerations/audience.model';

export interface ISubject {
  id?: number;
  topic?: ConferenceTopic;
  difficulty?: number;
  audience?: Audience;
}

export const defaultValue: Readonly<ISubject> = {};
