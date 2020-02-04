import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <MenuItem icon="asterisk" to="/conference">
      <Translate contentKey="global.menu.entities.conference" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/place">
      <Translate contentKey="global.menu.entities.place" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/room">
      <Translate contentKey="global.menu.entities.room" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/talk">
      <Translate contentKey="global.menu.entities.talk" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/talk-tag">
      <Translate contentKey="global.menu.entities.talkTag" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/file">
      <Translate contentKey="global.menu.entities.file" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/file-review">
      <Translate contentKey="global.menu.entities.fileReview" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/topic">
      <Translate contentKey="global.menu.entities.topic" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/subject">
      <Translate contentKey="global.menu.entities.subject" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/schedule-item">
      <Translate contentKey="global.menu.entities.scheduleItem" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/fee">
      <Translate contentKey="global.menu.entities.fee" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/talk-participant">
      <Translate contentKey="global.menu.entities.talkParticipant" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/presenter">
      <Translate contentKey="global.menu.entities.presenter" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/talk-history">
      <Translate contentKey="global.menu.entities.talkHistory" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
