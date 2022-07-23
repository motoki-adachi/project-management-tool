import React, { Component } from 'react';
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { logout } from "../../actions/securityActions";

class Landing extends Component {

    componentDidMount() {
        if (this.props.security.validToken) {
            this.props.history.push("/dashboard");
        }
    }

    render() {
        return (
            <div className="landing">
                <div className="light-overlay landing-inner text-dark">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-12 text-center">
                                <h3 className="mb-4">プロジェクト管理ツール</h3>
                                <p className="lead">
                                    アカウントを作成してアクティブなプロジェクトに参加するか、自分でプロジェクトを開始します
                                </p>
                                <hr />
                                <div>
                                    <Link to="/register" className="btn btn-lg btn-primary mb-2">
                                        <span className="p-4">新規登録</span>
                                    </Link>
                                </div>
                                <div>
                                    <Link to="/login" className="btn btn-lg btn-secondary mb-2">
                                        <span className="p-4">ログイン</span>
                                    </Link>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

Landing.propTypes = {
    security: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    security: state.security
});

export default connect(mapStateToProps)(Landing);
