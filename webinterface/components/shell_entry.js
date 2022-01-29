export default function ShellEntry(props) {
	return (
		<div style={{
			margin: '0 auto',
			position: 'relative'
		}} dangerouslySetInnerHTML={{ __html: props.entry.replace(/\n/g, "<br>") }}>
		</div>
	)
}