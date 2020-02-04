import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Conference from './conference';
import Place from './place';
import Room from './room';
import Talk from './talk';
import TalkTag from './talk-tag';
import File from './file';
import FileReview from './file-review';
import Topic from './topic';
import Subject from './subject';
import ScheduleItem from './schedule-item';
import Fee from './fee';
import TalkParticipant from './talk-participant';
import Presenter from './presenter';
import TalkHistory from './talk-history';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}conference`} component={Conference} />
      <ErrorBoundaryRoute path={`${match.url}place`} component={Place} />
      <ErrorBoundaryRoute path={`${match.url}room`} component={Room} />
      <ErrorBoundaryRoute path={`${match.url}talk`} component={Talk} />
      <ErrorBoundaryRoute path={`${match.url}talk-tag`} component={TalkTag} />
      <ErrorBoundaryRoute path={`${match.url}file`} component={File} />
      <ErrorBoundaryRoute path={`${match.url}file-review`} component={FileReview} />
      <ErrorBoundaryRoute path={`${match.url}topic`} component={Topic} />
      <ErrorBoundaryRoute path={`${match.url}subject`} component={Subject} />
      <ErrorBoundaryRoute path={`${match.url}schedule-item`} component={ScheduleItem} />
      <ErrorBoundaryRoute path={`${match.url}fee`} component={Fee} />
      <ErrorBoundaryRoute path={`${match.url}talk-participant`} component={TalkParticipant} />
      <ErrorBoundaryRoute path={`${match.url}presenter`} component={Presenter} />
      <ErrorBoundaryRoute path={`${match.url}talk-history`} component={TalkHistory} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
