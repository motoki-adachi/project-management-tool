import React, { Component } from 'react';
import { Link } from "react-router-dom";
import { deleteProjectTask } from "../../../actions/backlogActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";

class ProjectTask extends Component {

    onDeleteClick(backlogId, projectTaskId) {
        this.props.deleteProjectTask(backlogId, projectTaskId);
    }

    render() {
        const { project_task } = this.props;
        let priorityClass;
        let priorityString;

        switch (project_task.priority) {
            case 1:
                priorityClass = "bg-danger text-light";
                priorityString = "高";
                break;
            case 2:
                priorityClass = "bg-warning text-light";
                priorityString = "中";
                break;
            case 3:
                priorityClass = "bg-info text-light";
                priorityString = "低";
                break;
        }

        return (
            <div className="card mb-3 bg-light">
                <div className={`card-header text-primary ${priorityClass}`}>
                    ID: {project_task.projectSequence} -- Priority: {priorityString}
                </div>
                <div className="card-body bg-light shadow-lg">
                    <h5 className="card-title">{project_task.summary}</h5>
                    <p className="card-text text-truncate">
                        {project_task.acceptanceCriteria}
                    </p>
                    <Link to={`/updateProjectTask/${project_task.projectIdentifier}/${project_task.projectSequence}`} className="btn btn-primary">
                        確認 / 更新
                    </Link>
                    <span className="m-2"></span>
                    <button className="btn btn-danger"
                        onClick={this.onDeleteClick.bind(this, project_task.projectIdentifier, project_task.projectSequence)}>
                        削除
                    </button>
                </div>
            </div>
        )
    }
}

ProjectTask.propTypes = {
    deleteProjectTask: PropTypes.func.isRequired
};

export default connect(null, { deleteProjectTask })(ProjectTask);
