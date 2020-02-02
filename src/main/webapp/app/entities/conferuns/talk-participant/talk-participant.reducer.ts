import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITalkParticipant, defaultValue } from 'app/shared/model/conferuns/talk-participant.model';

export const ACTION_TYPES = {
  SEARCH_TALKPARTICIPANTS: 'talkParticipant/SEARCH_TALKPARTICIPANTS',
  FETCH_TALKPARTICIPANT_LIST: 'talkParticipant/FETCH_TALKPARTICIPANT_LIST',
  FETCH_TALKPARTICIPANT: 'talkParticipant/FETCH_TALKPARTICIPANT',
  CREATE_TALKPARTICIPANT: 'talkParticipant/CREATE_TALKPARTICIPANT',
  UPDATE_TALKPARTICIPANT: 'talkParticipant/UPDATE_TALKPARTICIPANT',
  DELETE_TALKPARTICIPANT: 'talkParticipant/DELETE_TALKPARTICIPANT',
  RESET: 'talkParticipant/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITalkParticipant>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type TalkParticipantState = Readonly<typeof initialState>;

// Reducer

export default (state: TalkParticipantState = initialState, action): TalkParticipantState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_TALKPARTICIPANTS):
    case REQUEST(ACTION_TYPES.FETCH_TALKPARTICIPANT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TALKPARTICIPANT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TALKPARTICIPANT):
    case REQUEST(ACTION_TYPES.UPDATE_TALKPARTICIPANT):
    case REQUEST(ACTION_TYPES.DELETE_TALKPARTICIPANT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_TALKPARTICIPANTS):
    case FAILURE(ACTION_TYPES.FETCH_TALKPARTICIPANT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TALKPARTICIPANT):
    case FAILURE(ACTION_TYPES.CREATE_TALKPARTICIPANT):
    case FAILURE(ACTION_TYPES.UPDATE_TALKPARTICIPANT):
    case FAILURE(ACTION_TYPES.DELETE_TALKPARTICIPANT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_TALKPARTICIPANTS):
    case SUCCESS(ACTION_TYPES.FETCH_TALKPARTICIPANT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TALKPARTICIPANT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TALKPARTICIPANT):
    case SUCCESS(ACTION_TYPES.UPDATE_TALKPARTICIPANT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TALKPARTICIPANT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/talk-participants';
const apiSearchUrl = 'api/_search/talk-participants';

// Actions

export const getSearchEntities: ICrudSearchAction<ITalkParticipant> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_TALKPARTICIPANTS,
  payload: axios.get<ITalkParticipant>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<ITalkParticipant> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TALKPARTICIPANT_LIST,
  payload: axios.get<ITalkParticipant>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ITalkParticipant> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TALKPARTICIPANT,
    payload: axios.get<ITalkParticipant>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITalkParticipant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TALKPARTICIPANT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITalkParticipant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TALKPARTICIPANT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITalkParticipant> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TALKPARTICIPANT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
