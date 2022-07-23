import React, { Component } from 'react';
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import classnames from 'classnames';
import { addProjectTask } from "../../../actions/backlogActions";
import PropTypes from "prop-types";

class AddProjectTask extends Component {

    constructor(props) {
        super(props)
        const { id } = this.props.match.params;
        this.state = {
            summary: "",
            acceptanceCriteria: "",
            status: "",
            priority: 0,
            dueDate: "",
            projectIdentifier: id,
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
            });
        }
    };

    onChange(e) {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    onSubmit(e) {
        e.preventDefault();
        const newTask = {
            summary: this.state.summary,
            acceptanceCriteria: this.state.acceptanceCriteria,
            status: this.state.status,
            priority: this.state.priority,
            dueDate: this.state.dueDate,
        }
        this.props.addProjectTask(this.state.projectIdentifier, newTask, this.props.history);
    }

    render() {
        const { id } = this.props.match.params;
        const { errors } = this.state;
        return (
            <div className="add-PBI">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <Link to={`/projectBoard/${id}`} className="btn btn-light">
                                ←プロジェクトボードに戻る
                            </Link>
                            <h2 className="text-center">タスクの作成</h2>
                            <p className="lead text-center">Project Name + Project Code</p>
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group mb-3">
                                    <input type="text"
                                        className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.summary
                                        })}
                                        name="summary"
                                        placeholder="タスクの概要"
                                        value={this.state.summary}
                                        onChange={this.onChange} />
                                    {errors.summary && (
                                        <div className="invalid-feedback">{errors.summary}</div>
                                    )}
                                </div>
                                <div className="form-group mb-3">
                                    <textarea
                                        className="form-control form-control-lg"
                                        placeholder="タスクの完了基準"
                                        name="acceptanceCriteria"
                                        value={this.state.acceptanceCriteria}
                                        onChange={this.onChange} >
                                    </textarea>
                                </div>
                                <h6>期日</h6>
                                <div className="form-group mb-3">
                                    <input type="date"
                                        className="form-control form-control-lg"
                                        name="dueDate"
                                        value={this.state.dueDate}
                                        onChange={this.onChange} />
                                </div>
                                <h6>優先度</h6>
                                <div className="form-group mb-3">
                                    <select
                                        className="form-control form-control-lg"
                                        name="priority"
                                        value={this.state.priority}
                                        onChange={this.onChange}>
                                        <option value={0}>未選択</option>
                                        <option value={1}>高</option>
                                        <option value={2}>中</option>
                                        <option value={3}>低</option>
                                    </select>
                                </div>
                                <h6>状態</h6>
                                <div className="form-group mb-3">
                                    <select
                                        className="form-control form-control-lg"
                                        name="status"
                                        value={this.state.status}
                                        onChange={this.onChange}>
                                        <option value="">未選択</option>
                                        <option value="TO_DO">未着手</option>
                                        <option value="IN_PROGRESS">進行中</option>
                                        <option value="DONE">完了</option>
                                    </select>
                                </div>

                                <input type="submit" className="btn btn-primary btn-block mt-4" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

AddProjectTask.propTypes = {
    addProjectTask: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    errors: state.errors
});

export default connect(mapStateToProps, { addProjectTask })(AddProjectTask);
