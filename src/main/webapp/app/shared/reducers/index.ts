import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import conference, {
  ConferenceState
} from 'app/entities/conference/conference.reducer';
// prettier-ignore
import place, {
  PlaceState
} from 'app/entities/place/place.reducer';
// prettier-ignore
import room, {
  RoomState
} from 'app/entities/room/room.reducer';
// prettier-ignore
import talk, {
  TalkState
} from 'app/entities/talk/talk.reducer';
// prettier-ignore
import talkTag, {
  TalkTagState
} from 'app/entities/talk-tag/talk-tag.reducer';
// prettier-ignore
import file, {
  FileState
} from 'app/entities/file/file.reducer';
// prettier-ignore
import fileReview, {
  FileReviewState
} from 'app/entities/file-review/file-review.reducer';
// prettier-ignore
import topic, {
  TopicState
} from 'app/entities/topic/topic.reducer';
// prettier-ignore
import subject, {
  SubjectState
} from 'app/entities/subject/subject.reducer';
// prettier-ignore
import scheduleItem, {
  ScheduleItemState
} from 'app/entities/schedule-item/schedule-item.reducer';
// prettier-ignore
import fee, {
  FeeState
} from 'app/entities/fee/fee.reducer';
// prettier-ignore
import talkParticipant, {
  TalkParticipantState
} from 'app/entities/talk-participant/talk-participant.reducer';
// prettier-ignore
import presenter, {
  PresenterState
} from 'app/entities/presenter/presenter.reducer';
// prettier-ignore
import talkHistory, {
  TalkHistoryState
} from 'app/entities/talk-history/talk-history.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly conference: ConferenceState;
  readonly place: PlaceState;
  readonly room: RoomState;
  readonly talk: TalkState;
  readonly talkTag: TalkTagState;
  readonly file: FileState;
  readonly fileReview: FileReviewState;
  readonly topic: TopicState;
  readonly subject: SubjectState;
  readonly scheduleItem: ScheduleItemState;
  readonly fee: FeeState;
  readonly talkParticipant: TalkParticipantState;
  readonly presenter: PresenterState;
  readonly talkHistory: TalkHistoryState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  conference,
  place,
  room,
  talk,
  talkTag,
  file,
  fileReview,
  topic,
  subject,
  scheduleItem,
  fee,
  talkParticipant,
  presenter,
  talkHistory,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
