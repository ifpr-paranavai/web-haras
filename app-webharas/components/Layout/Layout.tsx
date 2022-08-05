import styles from './Layout.module.scss';
import Header from "./Header/Header";


export default function Layout(props: any) {
  return (
    <>
      <Header />
      <div style={{height: "440px"}} className={`overflow-hidden flex items-center`}>
        <img className="w-screen object-center" src="./header.png" />
      </div>
      <div>
        {props.children}
      </div>
    </>
  );
}