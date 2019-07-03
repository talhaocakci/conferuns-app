import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Conference from './conferuns/conference';
import Fee from './conferuns/fee';
import File from './conferuns/file';
import FileReview from './conferuns/file-review';
import Place from './conferuns/place';
import Presenter from './conferuns/presenter';
import Room from './conferuns/room';
import ScheduleItem from './conferuns/schedule-item';
import Subject from './conferuns/subject';
import Talk from './conferuns/talk';
import TalkParticipant from './conferuns/talk-participant';
import TalkTag from './conferuns/talk-tag';
import Topic from './conferuns/topic';
import TalkHistory from './conferuns/talk-history';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/conference`} component={Conference} />
      <ErrorBoundaryRoute path={`${match.url}/fee`} component={Fee} />
      <ErrorBoundaryRoute path={`${match.url}/file`} component={File} />
      <ErrorBoundaryRoute path={`${match.url}/file-review`} component={FileReview} />
      <ErrorBoundaryRoute path={`${match.url}/place`} component={Place} />
      <ErrorBoundaryRoute path={`${match.url}/presenter`} component={Presenter} />
      <ErrorBoundaryRoute path={`${match.url}/room`} component={Room} />
      <ErrorBoundaryRoute path={`${match.url}/schedule-item`} component={ScheduleItem} />
      <ErrorBoundaryRoute path={`${match.url}/subject`} component={Subject} />
      <ErrorBoundaryRoute path={`${match.url}/talk`} component={Talk} />
      <ErrorBoundaryRoute path={`${match.url}/talk-participant`} component={TalkParticipant} />
      <ErrorBoundaryRoute path={`${match.url}/talk-tag`} component={TalkTag} />
      <ErrorBoundaryRoute path={`${match.url}/topic`} component={Topic} />
      <ErrorBoundaryRoute path={`${match.url}/talk-history`} component={TalkHistory} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
