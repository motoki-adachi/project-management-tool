import React, { Component } from 'react';
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { logout } from "../../actions/securityActions";

class Header extends Component {

    logout() {
        this.props.logout();
        window.location.href = "/";
    }

    render() {
        const { validToken, user } = this.props.security;

        const userIsAuthenticated = (
            <div className="float-right">
                <ul className="navbar-nav ml-auto">
                    <li className="nav-item">
                        <Link className="nav-link " to="/dashboard">
                            ダッシュボード
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link " to="/dashboard">
                            {user.fullName}
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link" to="/logout" onClick={this.logout.bind(this)}>
                            ログアウト
                        </Link>
                    </li>
                </ul>
            </div>
        );

        const userIsNotAuthenticated = (
            <div className="float-right">
                <ul className="navbar-nav ml-auto">
                    <li className="nav-item">
                        <Link className="nav-link " to="/register">
                            新規会員登録
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link" to="/login">
                            ログイン
                        </Link>
                    </li>
                </ul>
            </div>
        );

        let headerLinks;
        if (validToken && user) {
            headerLinks = userIsAuthenticated;
        } else {
            headerLinks = userIsNotAuthenticated;
        }

        return (
            <nav className="navbar navbar-expand-sm navbar-dark bg-primary mb-4">
                <div className="container">
                    <Link className="navbar-brand" to="/">
                        プロジェクト管理ツール
                    </Link>
                    {headerLinks}
                </div>
            </nav>
        )
    }
}

Header.propTypes = {
    logout: PropTypes.func.isRequired,
    security: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    security: state.security
});

export default connect(mapStateToProps, { logout })(Header);
