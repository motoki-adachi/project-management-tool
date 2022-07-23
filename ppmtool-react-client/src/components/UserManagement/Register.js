import React, { Component } from 'react';
import { createNewUser } from "../../actions/securityActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import classnames from "classnames";

class Register extends Component {

    constructor() {
        super();
        this.state = {
            username: "",
            fullName: "",
            password: "",
            confirmPassword: "",
            errors: {

            }
        };
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentDidMount() {
        if (this.props.security.validToken) {
            this.props.history.push("/dashboard");
        }
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.errors) {
            this.setState({
                errors: nextProps.errors
            })
        }
    }

    onChange(e) {
        this.setState({
            [e.target.name]: e.target.value
        })
    }

    onSubmit(e) {
        e.preventDefault();
        const newUser = {
            username: this.state.username,
            fullName: this.state.fullName,
            password: this.state.password,
            confirmPassword: this.state.confirmPassword,
        }
        this.props.createNewUser(newUser, this.props.history);
    }

    render() {
        const { errors } = this.state;
        return (
            <div className="register">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <h3 className="text-center">Sign Up</h3>
                            <p className="lead text-center">アカウントを作成する</p>
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group mb-3">
                                    <input type="text"
                                        className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.fullName
                                        })}
                                        placeholder="フルネーム"
                                        name="fullName"
                                        value={this.state.fullName}
                                        onChange={this.onChange}
                                    />
                                    {errors.fullName && (
                                        <div className="invalid-feedback">{errors.fullName}</div>
                                    )}
                                </div>
                                <div className="form-group mb-3">
                                    <input type="email"
                                        className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.username
                                        })}
                                        placeholder="メールアドレス"
                                        name="username"
                                        value={this.state.username}
                                        onChange={this.onChange}
                                    />
                                    {errors.username && (
                                        <div className="invalid-feedback">{errors.username}</div>
                                    )}
                                </div>
                                <div className="form-group mb-3">
                                    <input type="password"
                                        className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.password
                                        })}
                                        placeholder="Password"
                                        name="password"
                                        value={this.state.password}
                                        onChange={this.onChange}
                                    />
                                    {errors.password && (
                                        <div className="invalid-feedback">{errors.password}</div>
                                    )}
                                </div>
                                <div className="form-group mb-3">
                                    <input type="password"
                                        className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.confirmPassword
                                        })}
                                        placeholder="Password(確認用)"
                                        name="confirmPassword"
                                        value={this.state.confirmPassword}
                                        onChange={this.onChange}
                                    />
                                    {errors.confirmPassword && (
                                        <div className="invalid-feedback">{errors.confirmPassword}</div>
                                    )}
                                </div>
                                <div className="text-center">
                                    <input type="submit" className="btn-lg btn-primary text-white pl-4 pr-4" />
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

Register.propTypes = {
    createNewUser: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired,
    securuty: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    errors: state.errors,
    security: state.security
});

export default connect(mapStateToProps, { createNewUser })(Register);
