import { connect } from 'react-redux';
import SendMessageComponent from '../components/sendMessageComponent';

const mapStateToProps = (state) => {
    return {
        users: state.userReducer.users;
    };
}

const mapDispatchToProps = (dispatch) => {
    return {

    }
}

export default connect(mapStateToProps, mapDispatchToProps)(SendMessageComponent);