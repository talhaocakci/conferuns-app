import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <DropdownItem tag={Link} to="/entity/conference">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;<Translate contentKey="global.menu.entities.conferunsConference" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/place">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;<Translate contentKey="global.menu.entities.conferunsPlace" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/room">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;<Translate contentKey="global.menu.entities.conferunsRoom" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/talk">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;<Translate contentKey="global.menu.entities.conferunsTalk" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/talk-tag">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;<Translate contentKey="global.menu.entities.conferunsTalkTag" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/file">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;<Translate contentKey="global.menu.entities.conferunsFile" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/file-review">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;<Translate contentKey="global.menu.entities.conferunsFileReview" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/topic">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;<Translate contentKey="global.menu.entities.conferunsTopic" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/subject">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;<Translate contentKey="global.menu.entities.conferunsSubject" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/schedule-item">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;<Translate contentKey="global.menu.entities.conferunsScheduleItem" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/fee">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;<Translate contentKey="global.menu.entities.conferunsFee" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/talk-participant">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;<Translate contentKey="global.menu.entities.conferunsTalkParticipant" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/presenter">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;<Translate contentKey="global.menu.entities.conferunsPresenter" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/talk-history">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;<Translate contentKey="global.menu.entities.conferunsTalkHistory" />
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
