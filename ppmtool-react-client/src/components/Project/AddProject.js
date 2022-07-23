import React, { Component } from 'react'
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { createProject } from "../../actions/projectActions";
import classnames from "classnames";

class AddProject extends Component {

    constructor() {
        super()
        this.state = {
            projectName: "",
            projectIdentifer: "",
            description: "",
            startDate: "",
            endDate: "",
            errors: {

            }
        }
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
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
        const newProject = {
            projectName: this.state.projectName,
            projectIdentifer: this.state.projectIdentifer,
            description: this.state.description,
            startDate: this.state.startDate,
            endDate: this.state.endDate
        };
        this.props.createProject(newProject, this.props.history);
    }

    render() {
        const { errors } = this.state;
        return (
            <div>
                <div className="project">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-8 m-auto">
                                <h5>プロジェクトの登録</h5>
                                <hr />
                                <form onSubmit={this.onSubmit}>
                                    <div className="form-group mb-3">
                                        <input type="text"
                                            className={classnames("form-control form-control-lg", {
                                                "is-invalid": errors.projectName
                                            })}
                                            placeholder="プロジェクト名称"
                                            name="projectName"
                                            value={this.state.projectName}
                                            onChange={this.onChange} />
                                        {errors.projectName && (
                                            <div className="invalid-feedback">{errors.projectName}</div>
                                        )}
                                    </div>
                                    <div className="form-group mb-3">
                                        <input type="text"
                                            className={classnames("form-control form-control-lg", {
                                                "is-invalid": errors.projectIdentifer
                                            })}
                                            placeholder="プロジェクトID(重複不可)"
                                            name="projectIdentifer"
                                            value={this.state.projectIdentifer}
                                            onChange={this.onChange} />
                                        {errors.projectIdentifer && (
                                            <div className="invalid-feedback">{errors.projectIdentifer}</div>
                                        )}
                                    </div>
                                    <div className="form-group mb-3">
                                        <textarea
                                            className={classnames("form-control form-control-lg", {
                                                "is-invalid": errors.description
                                            })}
                                            placeholder="内容"
                                            name="description"
                                            value={this.state.description}
                                            onChange={this.onChange} >
                                        </textarea>
                                        {errors.description && (
                                            <div className="invalid-feedback">{errors.description}</div>
                                        )}
                                    </div>
                                    <h6>開始日</h6>
                                    <div className="form-group mb-3">
                                        <input type="date" className="form-control form-control-lg"
                                            name="startDate" value={this.state.startDate}
                                            onChange={this.onChange} />
                                    </div>
                                    <h6>終了日</h6>
                                    <div className="form-group mb-3">
                                        <input type="date" className="form-control form-control-lg"
                                            name="endDate" value={this.state.endDate}
                                            onChange={this.onChange} />
                                    </div>

                                    <input type="submit" className="btn btn-primary btn-block mt-4" />
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

AddProject.propTypes = {
    createProject: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    errors: state.errors
});

export default connect(
    mapStateToProps,
    { createProject }
)(AddProject);
