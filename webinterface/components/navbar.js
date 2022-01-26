import React from "react";
import { get_color } from "../js/style";

export default class Navbar extends React.Component {
	constructor(props) {
		super(props);
		this.state = { 
			navbar_shown: false,
			navbar_title: "",
		};

		window.addEventListener("ready", () => {
			this.setState({
				navbar_shown: this.state.navbar_shown,
				navbar_title: document.querySelector("title").innerHTML,
			});
		})
	}

	render() {
		var sites = [
			{
				name: 'Home',
				url: '/'
			},
			{
				name: 'Commands',
				url: '/commands'
			},
			{
				name: 'Api Playground',
				url: '/api_playground'
			}
		];


		return (
			<div>
				<div>
					<a className={"bar-item button right top padding-large " + get_color().color + " hover-" + get_color().base_color} style={{
						zIndex: 2,
						marginLeft: "auto"
					}} onClick={
						event => {
							event.preventDefault();
							this.setState({
								navbar_shown: !this.state.navbar_shown,
								navbar_title: this.state.navbar_title,
							});
						}
					}>&#9776;</a>

					<td className={"padding-large hide-small " + get_color().color} style={{
						verticalAlign: "top",
						textAlign: "left",
						zIndex: 3,
						top: 0,
						left: 0,
						position: "fixed",
					}}>
						{this.state.navbar_title}
					</td>
				</div>
				<div className="top" style={{
					"margin-top": "45px"
				}}>
				
					<div style={{
							display: this.state.navbar_shown ? "block" : "none"
					}} onMouseLeave={
						event => {
							event.preventDefault();
							this.setState({
								navbar_shown: false,
								navbar_title: this.state.navbar_title,
							});
						}
					}>
						<ul className={"bar card left-align " + get_color().base_color} style={{
							margin: "0px",
							padding: "0px"
						}}>
							{ Object.keys(sites).map(function(key) {
								return (
									<li style={{
										listStyle: "none",
										margin: "0px",
									}} className={"padding-large " +  get_color().color + " hover-" + get_color().base_color} onClick={
										event => {
											event.preventDefault();
											window.location.href = sites[key].url;
										}
									}>
										{sites[key].name}
									</li>
								);
							}) }
						</ul>

					</div>
				</div>
			</div>
		);
	}
}
