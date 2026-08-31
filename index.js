import { AppRegistry } from 'react-native';
import CreateTournamentScreen from './src/screens/CreateTournamentScreen';
import TournamentDetailScreen from './src/screens/TournamentDetailScreen';

AppRegistry.registerComponent('TournamentDetail', () => TournamentDetailScreen);
AppRegistry.registerComponent('CreateTournament', () => CreateTournamentScreen);
