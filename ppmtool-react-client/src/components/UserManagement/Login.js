import React, { Component } from 'react';
import { login } from "../../actions/securityActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import classnames from "classnames";

class Login extends Component {
    constructor() {
        super();
        this.state = {
            username: "",
            password: "",
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
        console.log(nextProps.security.validToken);
        if (nextProps.security.validToken) {
            this.props.history.push("/dashboard");
        }
    }

    onChange(e) {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    onSubmit(e) {
        e.preventDefault();
        const LoginRequest = {
            username: this.state.username,
            password: this.state.password,
        }
        this.props.login(LoginRequest);
    }

    render() {
        const { errors } = this.state;
        return (
            <div className="login">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <h3 className="text-center">Login</h3>
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group mb-3">
                                    <input type="text"
                                        className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.username
                                        })}
                                        placeholder="Email Address"
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

Login.propTypes = {
    login: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired,
    security: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    security: state.security,
    errors: state.errors,
})

export default connect(mapStateToProps, { login })(Login);
